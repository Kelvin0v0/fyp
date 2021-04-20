<?php

namespace App\Http\Controllers;

use App\Http\Controllers\Controller;
use Illuminate\Foundation\Auth\AuthenticatesUsers;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;
use App\User;
use App\Model\Account;

class MobileController extends Controller
{

    use AuthenticatesUsers;

    /**
     * Where to redirect users after login.
     *
     * @var string
     */
    protected $redirectTo = '/m2';
    /**
     * Create a new controller instance.
     *
     * @return void
     */
    public function __construct()
    {
        // $this->middleware('guest')->except('logout');
    }

    protected function authenticated(Request $request, $user)
    {
        $this->redirect('m2');
    }
    public function test(Request $request)
    {

        return response()->json([
            'status' => -1, 'message' => 'fail'
        ]);
    }

    public function login(Request $request)
    {

        $input = $request->all();

        $validator = Validator::make($request->all(), [
            'email' => 'required|email',
            'password' => 'required',
        ]);
        if ($validator->fails()) {
            return response()->json([
                'status' => -2, 'message' => 'Wrong username or password'
            ]);
        }

        if (auth()->attempt(array('email' => $input['email'], 'password' => $input['password']))) {


            $user = User::where('email', $input['email'])->first();

            if (!$user) {
                return response()->json([
                    'status' => -2, 'message' => "user not found"
                ]);
            }
            $account = Account::where(['uuid' => $input['uuid']])->where('user_id', $user->id)->first();

            if ($account) {

                $account->update(['last_login_at' => date("Y-m-d H:i:s", time())]);
                return response()->json([
                    'status' => 0, 'message' => 'success', 'balance' => $account->balance, 'name' => $user->name
                ]);
            }
            $newAccount = Account::where(['uuid' => $input['uuid']])->where('user_id', 0)->first();
            if ($newAccount) {
                $newAccount->update(['user_id' =>  $user->id, 'last_login_at' => date("Y-m-d H:i:s", time())]);
                return response()->json([
                    'status' => 0, 'message' => 'success', 'balance' => $newAccount->balance, 'name' => $user->name
                ]);
            }
            Account::create([
                'user_id' => $user->id,
                'uuid' => $input['uuid'],
                'last_login_at' => date("Y-m-d H:i:s", time()),
            ]);
            $newAccount = Account::where('user_id', $user->id)->where('uuid', $input['uuid'])->first();
            return response()->json([
                'status' => 0, 'message' => 'success', 'balance' => $newAccount->balance, 'name' => $user->name
            ]);
        }

        return response()->json([
            'status' => -1, 'message' => 'fail'
        ]);
    }
}
