<?php

namespace App\Http\Controllers;

use App\Model\Account;
use App\Model\Otp;
use App\Model\Transaction;
use App\Http\Controllers\Controller;
use App\Providers\RouteServiceProvider;
use App\User;
use Illuminate\Http\Request;
use Illuminate\Foundation\Auth\RegistersUsers;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\Validator;


class MobileGetOtpController extends Controller
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
    //     $this->middleware('guest');
    }



    /**
     * Create a new user instance after a valid registration.
     *
     * @param  array  $data
     * @return \App\User
     */
    protected function getOtp(Request $request)
    {   $account = Account::where('uuid', $request['uuid'])->orderBy('last_login_at','DESC')->first();
        $user = User::where('id',$account['user_id'])->first();
        $randNum = array();
        $dataSet = "0123456789ABCDEF";
        for($x = 0; $x < 2; $x++){
            array_push($randNum,$dataSet[rand(0,15)] );
        }
        $otp = implode("",$randNum);
        if($account){
            

            Otp::create([
                'user_id' => $account['user_id'],
                'uuid' => $account['uuid'],
                'code' => $otp,
                'expire_at' => date( "Y-m-d H:i:s", time()+(3*60)) 
            ]);

            Transaction::create([
                'sender_id' => 0,
                'receiver_id' => $account['user_id'],
                'amount' => $request['cost'],
                'status' => "transmitting",
                'type' => $user['is_merchant']?"payment":"transaction",
            ]);
            return  response()->json([ 'status' => 0, 'message' => $otp,'cost'=>$request['cost']]);
        }
        

        
    }
}
