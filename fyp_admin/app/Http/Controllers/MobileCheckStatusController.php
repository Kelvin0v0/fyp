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


class MobileCheckStatusController extends Controller
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
    protected function checkPayment(Request $request)
    {   $account = Account::where('uuid', $request['uuid'])->orderBy('last_login_at','DESC')->first();
        $otp = Otp::where('code',$request['otp'])->orderBy('created_at','DESC')->first();

        $transaction = Transaction::where('created_at', $otp['created_at'])->where('receiver_id',$account['user_id'])->first();
        if($transaction){
            if( strcmp($transaction['status'], "completed") == 0){
                $sender = User::where('id',$transaction['sender_id'])->first();
                $receiver = User::where('id',$transaction['receiver_id'])->first();
                return  response()->json([ 'status' => 0, 'message' => $transaction->status, 'sender'=> $sender->name, 'receiver'=>$receiver->name , 'amount'=> $transaction->amount]);
            }else{
                return  response()->json([ 'status' => 0, 'message' => $transaction->status]);
            }
        }else{
            return  response()->json([ 'status' => -1, 'message' => 'no record']);
        }
       
        
        

        
    }
}
