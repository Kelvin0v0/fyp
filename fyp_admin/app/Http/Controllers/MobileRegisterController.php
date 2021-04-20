<?php

namespace App\Http\Controllers;

use App\Http\Controllers\Controller;
use App\Providers\RouteServiceProvider;
use Illuminate\Http\Request;
use App\User;
use App\Model\Account;
use Illuminate\Foundation\Auth\RegistersUsers;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\Validator;

class MobileRegisterController extends Controller
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
            'name' => ['required', 'string', 'max:255'],
            // 'uuid' => ['required', 'string', 'max:255'],
            'email' => ['required', 'string', 'email', 'max:255', 'unique:users'],
            'password' => ['required', 'string', 'min:8', 'confirmed'],
        ]);
    }

    /**
     * Create a new user instance after a valid registration.
     *
     * @param  array  $data
     * @return \App\User
     */
    protected function register(Request $request)
    {
       
         User::create([
            'name' => $request['name'],
            'email' => $request['email'],
            'password' => Hash::make($request['password']),
            'is_merchant' => $request['is_merchant'],
            'phone_num' => $request['phone_num']
        ]);
        

        $user = User::where('name', $request['name'])->where('email', $request['email'])->first();

        if(!$user){
             return response()->json([
                'status' => -2, 'message' => "user not found"
                ]);
        }
        // $account = Account::firstOrNew(['uuid'=>$request['uuid']]);
        // if ($account->user_id == 0) {
        //     $account->user_id = $user->id;
        //     $account->save();
        // }else{
        //     if($account->user_id == $user->id){
        //         return response()->json([
        //             'status' => 0, 'message' => 'success', 'balance' => $account->balance
        //         ]);
        //     }else{
        //         return response()->json([
        //             'status' => -2, 'message' => "This phone is already registered"
        //         ]);
        //     }
        // }

        return response()->json([
            'status' => 0, 'message' => "success registration"
            ]);
        
        
    }
}
