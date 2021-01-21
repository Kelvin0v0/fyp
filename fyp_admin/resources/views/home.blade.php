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
    function routeToTransaction() {
        event.preventDefault();
        const CSRF_TOKEN = $('meta[name="csrf-token"]').attr('content')
        $.ajax({
            url: '/home/transaction',
            type: 'get',
            data: {
                CSRF_TOKEN
            },
            success: function(data) {
                $("#display").html(data)
            }
        })

    }

    function routeToDashboard() {
        event.preventDefault();
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