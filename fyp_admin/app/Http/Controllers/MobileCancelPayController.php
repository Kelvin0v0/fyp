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

class MobileCancelPayController extends Controller
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
    protected function cancelPay(Request $request)
    {   
        $otp = Otp::where('code',$request['otp'])->orderBy('created_at','DESC')->first();
        echo $otp;
        $account = Account::where('uuid',$request['uuid'])->first();
        $transaction = Transaction::where('created_at',$otp['created_at'])->where('receiver_id',$otp['user_id'])->where('amount',$request['cost'])->first();
        if($transaction){
                $transaction->update(['status'=>'cancelled']);

                return  response()->json([
                    'status' => 0, 'message' => "cancel success"]);
        }else{
            //account may not correct 
            return  response()->json([
                'status' => -1, 'message' => 'fail to complete' ]);
        }
        
        
        
                
        
       
    }
}