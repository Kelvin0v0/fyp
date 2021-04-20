<?php

namespace App\Http\Controllers;

use App\Http\Controllers\Controller;
use App\Providers\RouteServiceProvider;
use Illuminate\Http\Request;
use App\User;
use App\Model\Account;
use App\Model\Otp;
use App\Model\Transaction;
use Illuminate\Foundation\Auth\RegistersUsers;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\Validator;

class MobileSetOtpController extends Controller
{
    /*
    |--------------------------------------------------------------------------
    | Register Controller
    |--------------------------------------------------------------------------
    |
    | This controller handles the registration of new users as well as their
    | validation and creation. By default this controller uses a trait to
    | provide this functionality without requiring any additional code.
    |
    */

    use RegistersUsers;

    /**
     * Where to redirect users after registration.
     *
     * @var string
     */
    // protected $redirectTo = RouteServiceProvider::HOME;
    protected $redirectTo = '/m2';

    /**
     * Create a new controller instance.
     *
     * @return void
     */
    public function __construct()
    {
        $this->middleware('guest');
    }

    /**
     * Get a validator for an incoming registration request.
     *
     * @param  array  $data
     * @return \Illuminate\Contracts\Validation\Validator
     */
    protected function validator(array $data)
    {
        return Validator::make($data, [
            'uuid' => ['required', 'string', 'max:255'],
            'otp' => ['required', 'string', 'min:8'],
        ]);
    }

    /**
     * Create a new user instance after a valid registration.
     *
     * @param  array  $data
     * @return \App\User
     */
    protected function CheckTran(Request $request)
    {   $code = substr($request['otp'], 1, 2); 
        $otp = Otp::where('code',$code)->orderBy('created_at','DESC')->first();
        $account = Account::where('uuid',$request['uuid'])->orderBy('last_login_at','DESC')->first();
        $user = User::where('id',$otp['user_id'])->first();
        if($otp){
            if(strtotime($otp['expire_at']) <= time()){
                return response()->json([
                    'status' => -1, 'message' => 'otp is expired', "otpTime"=>$otp['expire_at'],"stro"=>strtotime($otp['expire_at']) ,'current'=>time()
                ]);
            }else{
                Transaction::where('receiver_id',$otp['user_id'])->where('created_at',$otp['created_at'])->update(['sender_id' => $account['user_id'],'status'=>'paying']);
                $transaction = Transaction::where('receiver_id',$otp['user_id'])->where('created_at',$otp['created_at'])->first();
                return  response()->json([
                    'status' => 0, 'message' => 'transaction confirm','receiver'=> $user['name'], 'cost'=> $transaction['amount'],'otp'=>$otp['code'] ]);
                
            }
        }else{
            return response()->json([
                'status' => -1, 'message' => 'otp is not exist'
            ]);

        }
    }
}
