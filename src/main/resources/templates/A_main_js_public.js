var loginbtn = document.getElementById("navbtn1h");
var signUpbtn = document.getElementById("navbtn2h");

//public homepage
function loginPage() {
    $("#initialSignUpForm").hide();
    $("#publicHomePage").hide();
    $("#loginForm").show();
    console.log("login btn");
  }
  function signUpPage() {
    $("#publicHomePage").hide();
    $("#loginForm").hide();
    $("#initialSignUpForm").show();
  }

  //call function when document is ready
document.addEventListener("DOMContentLoaded", () => {
    $("#loginForm").hide();
    $("#initialSignUpForm").hide();
    $("#publicHomePage").show();
  });

//---------
  //Native SignUp form
  $(document).ready(function () {
    $("#initialSignUpBtn").click(function () {
      var name = $("#name").val();
      var username = $("#username").val();
      var email=$("#email").val();
      var password = $("#password").val();
      var dob = $("#dob").val();
      var role = $('input:radio[name=rb1]:checked').val();
      var address = $("#address").val();
      var profession = $("input:radio[name=rb2]:checked").val();
      var address = $("#address").val();
      //var profileImg = $('input[name="profileImg"]').get(0).files[0];

      var jsonData = { name:name,username: username,email:email, password: password,
                        dob:dob,role:role ,address:address,profession,profession,address:address};
                        console.log(jsonData);
      jsonData = JSON.stringify(jsonData);
      console.log(jsonData);
  
      const xhr = new XMLHttpRequest();
      xhr.open("POST", "http://localhost:8080/initialSignup");
      xhr.setRequestHeader("Content-Type", "application/json");
      xhr.send(jsonData);
      xhr.onreadystatechange=function(){
        if(this.readyState==4){
          if(this.status==200){
            alert("Initial signup successfull");
          }else{
            alert("Inital signup unsuccessful");
          }
        }
      }
  
      loginPage();
    });
  });


  // Google Sign in
  function onSignIn(googleUser) {
    console.log("onsignin success");
    var profile = googleUser.getBasicProfile();
    console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
    console.log('Name: ' + profile.getName());
    console.log('Image URL: ' + profile.getImageUrl());
    console.log('Email: ' + profile.getEmail()); // This is null if the 'email' scope is not present.
    $("#usernamelogin").val(profile.getEmail());
    $("#loginType").val("googleSignin");
    document.getElementById("btnsubmit").click();
  }



  