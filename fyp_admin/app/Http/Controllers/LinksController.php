<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use SebastianBergmann\CodeCoverage\Report\Html\Dashboard;
use DB;
use App\Model\Transaction;

class LinksController extends Controller
{
    /**
     * Create a new controller instance.
     *
     * @return void
     */
    public function getDashboard(request $request)
    {
        return view('/content/dashboard');
    }

    public function getTransaction(request $request)
    {
        $trans_timestamp =  $request['trans_timestamp'] ?? "";
        $query = Transaction::join('users as receiver', 'receiver.id', '=', 'transaction.receiver_id')
            ->join('users as sender', 'sender.id', '=', 'sender_id');

        /*if ($trans_timestamp != "") {
            $query  = $query->where('created_at', '>=', $trans_timestamp);
        }*/
        $tables = $query->select(['transaction.*',  'sender.name as sender', 'receiver.name as receiver'])
            ->get();

        return view('/content/transaction', [
            'date_str' => $trans_timestamp,
            'tables' => $tables
        ]);
    }
    public function setInterval($f, $milliseconds)
    {
        $seconds = (int)$milliseconds / 1000;
        while (true) {
            $f();
            sleep($seconds);
        }
    }
}
