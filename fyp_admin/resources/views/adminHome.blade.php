@extends('layouts.app')
<style>
    body {
        overflow-x: hidden;
    }

    #sidebar-wrapper {
        min-height: 100vh;
        margin-left: -15rem;
        -webkit-transition: margin .25s ease-out;
        -moz-transition: margin .25s ease-out;
        -o-transition: margin .25s ease-out;
        transition: margin .25s ease-out;
    }

    #sidebar-wrapper .sidebar-heading {
        padding: 0.875rem 1.25rem;
        font-size: 1.2rem;
    }

    #sidebar-wrapper .list-group {
        width: 15rem;
    }

    #page-content-wrapper {
        min-width: 100vw;
    }

    #wrapper.toggled #sidebar-wrapper {
        margin-left: 0;
    }

    @media (min-width: 768px) {
        #sidebar-wrapper {
            margin-left: 0;
        }

        #page-content-wrapper {
            min-width: 0;
            width: 100%;
        }

        #wrapper.toggled #sidebar-wrapper {
            margin-left: -15rem;
        }
    }
</style>
@section('content')

<div class="container">
    <div class="d-flex" id="wrapper">

        <!-- Sidebar -->
        <div class="bg-light border-right" id="sidebar-wrapper">
            <div class="sidebar-heading">Portal</div>
            <div class="list-group list-group-flush">
                <a href="./dashboard" class="list-group-item list-group-item-action bg-light" onclick="routeToDashboard()">Dashboard</a>
                <a href="#" class="list-group-item list-group-item-action bg-light" onclick="routeToTransaction()">Transaction</a>
                <a href="#" class="list-group-item list-group-item-action bg-light">Alert</a>
            </div>
        </div>
        <!-- /#sidebar-wrapper -->

        <!-- Page Content -->
        <div id="page-content-wrapper">

            <div class="container-fluid">

                <!-- <div class="col-md-8"> -->
                <div id="display"></div>
                <!-- </div> -->

            </div>
        </div>
        <!-- /#page-content-wrapper -->

    </div>


</div>

<script>
    var timer;
    var trans_timestamp = "";

    function getData(callback) {
        const CSRF_TOKEN = $('meta[name="csrf-token"]').attr('content')
        $.ajax({
            url: '/home/transaction',
            type: 'get',
            dataType: 'json',
            data: {
                trans_timestamp,
                CSRF_TOKEN

            },
            success: function(json) {
                let {
                    data = [], date = ""
                } = json;

                $("span", "#display").html(date);
                let html = "";
                for (let row of data) {
                    html = row + html;
                }
                let tbody = $("tbody", "#display");
                tbody.html(html);
               

                trans_timestamp = date;

                if (typeof(callback) == "function") {
                    callback();
                }

            }
        })
    }

    function mkTable() {
        return 'Time: <span></span> <br /><table class="table">  <thead class="table-light">    <tr>      <th scope="col">TranNo</th>      <th scope="col" style="width: 100px">Sender</th>      <th scope="col" style="width: 100px">Receiver</th>      <th scope="col">amount</th>      <th scope="col">Created Date</th>      <th scope="col">Updated Date</th>      <th scope="col">Status</th>      <th scope="col">Type</th>    </tr>  </thead>  <tbody>  </tbody></table>';
    }


    function routeToTransaction() {
        event.preventDefault();
        $("#display").html(mkTable());
        getData(() => {
            if (timer !== undefined) {
                clearInterval(timer);
            }
            timer = setInterval(() => {
                getData();
            }, 2000);
        });
    }

    function routeToDashboard() {
        event.preventDefault();
        if (timer !== undefined) {
            clearInterval(timer);
        }

        const CSRF_TOKEN = $('meta[name="csrf-token"]').attr('content')
        $.ajax({
            url: '/home/dashboard',
            type: 'get',
            data: {
                CSRF_TOKEN
            },
            success: function(data) {
                $("#display").html(data)
            }
        })

    }
</script>
@endsection