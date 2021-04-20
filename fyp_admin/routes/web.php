<?php

use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

Route::get('/', function () {
    return view('welcome');
});

Auth::routes();


Route::match(['GET','POST'],'ml','MobileController@logout')->name('ml');
Route::match(['GET','POST'],'tran','MobileSetOtpController@CheckTran')->name('tran');
Route::match(['GET','POST'],'vib','MobileGetOtpController@getOtp')->name('vib');
Route::match(['GET','POST'],'s','MobileRegUuidController@register')->name('s');
Route::match(['GET','POST'],'mr','MobileRegisterController@register')->name('mr');
Route::match(['GET','POST'],'m2','MobileController@test')->name('m2');
Route::match(['GET','POST'],'m','MobileController@login')->name('m');
Route::match(['GET','POST'],'cPay','MobileConfirmPayController@pay')->name('cPay');
Route::match(['GET','POST'],'clPay','MobileCancelPayController@cancelPay')->name('clPay');
Route::match(['GET','POST'],'h','MobileHistoryController@getHistory')->name('h');
Route::match(['GET','POST'],'b','MobileGetBalanceController@getBalance')->name('b');
Route::match(['GET','POST'],'check','MobileCheckStatusController@checkPayment')->name('check');

Route::get('/home', 'HomeController@index')->name('home');
Route::get('admin/home', 'HomeController@adminHome')->name('admin.home')->middleware('is_admin');
Route::get('/home/dashboard','LinksController@getDashboard');
Route::get('/home/transaction','LinksController@getTransaction');

