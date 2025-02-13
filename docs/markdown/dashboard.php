<!DOCTYPE html>
<html lang="en">

<head>

    <script type="text/javascript">

      if(!localStorage.token){ //if empty
          window.location.href = '/cloudstart/login.php'
      }

    // var menuBar = document.getElementById('cs-menubar');

    // if()
    // menuBar.innerHTML = '<li>'+
    //                         '<a href="https://metamug.com/cloudstart/" >SIGN UP</a>'+
    //                     '</li>'+
    //                     '<li>'+
    //                         '<a href="/cloudstart/login.php" >LOGIN</a>'+
    //                     '</li>';

    
</script>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
<meta charset="utf-8">
<link href="https://s3-ap-southeast-1.amazonaws.com/console.metamug.com/assets/vendor/bootstrap.min.css" rel="stylesheet" />
<link rel="stylesheet" href="/font-awesome/css/font-awesome.min.css" type="text/css">
<script src="/js/jquery.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<title>Dashboard</title>
<body>
 <style type="text/css">
    .navbar-inverse .navbar-nav>li>a {
        color: #fafafa;
    }

    .navbar-inverse .navbar-nav>li>a:hover {
        color: #18bc9c;
    }
</style>

<nav id="mainNav" class="navbar navbar-inverse navbar-fixed-top" style="background-color: #333;">

 <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="https://metamug.com/cloudstart/dashboard.php"><span class="text-success"><i class="fa fa-cube fa-fw"></i>metamug</span> <span style="#fafafa">cloudstart</span> </a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse">
        <ul class="nav navbar navbar-nav navbar-right" id="cs-menubar"> 
            <li  role="presentation" >
                <a href="/cloudstart/account.php">ACCOUNT</a>
            </li>
            <li  role="presentation" >
                <a href="/cloudstart/logout.php" >LOGOUT</a>
            </li>               
        </ul>
    </div>
    <!-- /.navbar-collapse -->
</div>
<!-- /.container-fluid -->
</nav>
<div style="margin-bottom: 5em;">.</div>

<main class="container" style="padding-top:0.5em; padding-bottom: 6em;">
	<div class="row">
		<div class="col-md-12">
          <h1 style="font-size: 50px">Thank you for registering on metamug the service will go live on 2nd October 2017 </h1>
      </div>

      <div class="col-md-8">
          <div class="panel panel-default">
            <div class="panel-heading"><h4>My Server</h4></div>
            <div class="panel-body">
                <div class="col-md-12">
                    <form id="myserver" class="form-horizontal" role="form">
                        <fieldset>
                            <div class="form-group">
                                <div class="col-sm-4 control-label">Type</div>
                                <div class="col-sm-4 control-label">Small Server T2</div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-4 control-label">Hardware Type</div>
                                <div class="col-sm-4 control-label">2gb</div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-4 control-label">Name</div>
                                <div class="col-sm-4 control-label">T2 small</div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-4 control-label">Backends</div>
                                <div class="col-sm-4 control-label">2</div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-4 control-label">Status</div>
                                <div class="col-sm-4 control-label">Inactive</div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-4 control-label">Issue Date</div>
                                <div class="col-sm-4 control-label">25-Aug-2017</div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-4 control-label">Expiry Date</div>
                                <div class="col-sm-4 control-label">24-Aug/2018</div>
                            </div>
                            <div>
                                <a href="https://metamug.com/cloudstart/servers.php"><input type="button" class="btn btn-success" value="Buy Server" style="margin-left: 40em" /></a>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </main>

        <?php include "../fragments/footer.php"; ?>
<!-- <table class="table table-striped table-hover table-bordered">
            <tbody>
              <tr>
                <th>#</th>
                <th>Type</th>
                <th>Hardware Type</th>
                <th>Name</th>
                <th>Status</th>
                <th>Issue Date</th>
                <th>Expiry Date</th>
                <th></th>
              </tr>
              <tr>
                <td><input type="checkbox" value=""></td>
                <td>Small Server T2</td>
                <td></td>
                <td>T2 small</td>
                <td>Inactive</td>
                <td>25-Aug-2017</a></td>
                <th>24-Aug/2018</th>
                <th><a href="#" class="pull-right btn btn-success">Renew</a></th>
              </tr>
            </tbody>
          </table>           -->