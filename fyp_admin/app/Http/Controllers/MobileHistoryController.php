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


class MobileHistoryController extends Controller
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
    protected function getHistory(Request $request)
    {
        $account = Account::where('uuid', $request['uuid'])->orderBy('last_login_at','DESC')->first();
        $history = Transaction::join('users as receiver', 'receiver.id', '=', 'transaction.receiver_id')
            ->join('users as sender', 'sender.id', '=', 'sender_id')
            ->whereRaw(" (sender_id = ? or receiver_id = ?) ", [$account['user_id'], $account['user_id']])
            ->where('status','completed')
            ->orderBy('transaction.created_at', "DESC")
            ->select(['transaction.sender_id', 'receiver_id', 'sender.name as sender', 'receiver.name as receiver', 'transaction.amount',  'transaction.created_at'])
            ->get();


        // $history['created_at'] = $history->created_at->format('M-d-Y');
        // where("sender_id",$account['user_id'])->get(['id','receiver_id', 'amount', 'created_at'])]);
        // echo $history['created_at']->date('YYYY-MM-DD');
        foreach ($history as $key => $row) {
            $history[ $key]['created_at']= $history[ $key]['created_at']->setTimezone(new \DateTimeZone('Hongkong'))->format('Y-m-d H:i');
        }
        return  response()->json(['status' => 0, 'message' => $history]);
    }
}
