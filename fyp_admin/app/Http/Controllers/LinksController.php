<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use SebastianBergmann\CodeCoverage\Report\Html\Dashboard;
use DB;

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
        $tables = DB::select('select * from transaction');
        return view('/content/transaction', ['tables' => $tables]);
    }
}
