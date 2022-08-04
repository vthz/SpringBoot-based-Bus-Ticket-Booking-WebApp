// button variables
var homebtn = document.getElementById("navbtn1");
var showjourneybtn = document.getElementById("navbtn2");
var addnewtripbtn = document.getElementById("navbtn3");

var userhomebtn = document.getElementById("usernavbtn1");
var userrenewbtn = document.getElementById("usernavbtn2");
var userbookticketbtn = document.getElementById("usernavbtn3");

//Admin Pages Function/ Ajax Window
function homepage() {
  loaduser();
  console.log("clicked");
  $("#login_formdiv").hide();
  $("#newtrip_formdiv").hide();
  $("#allTripsTableDiv").hide();
  $("#profileSection").hide();
  $("#home_pagediv").show();
}
function allTrips() {
  loadtrips();
  $("#placeo").empty();
  $("#placed").empty();
  $("#home_pagediv").hide();
  $("#login_formdiv").hide();
  $("#newtrip_formdiv").hide();
  $("#profileSection").hide();
  $("#allTripsTableDiv").show();
}
function newtrip() {
  $("#home_pagediv").hide();
  $("#login_formdiv").hide();
  $("#allTripsTableDiv").hide();
  $("#profileSection").hide();
  $("#newtrip_formdiv").show();
}

function profileSectionAdmin() {
  getUserDetails();
  getProfileImage();
  document.getElementById("name").readOnly=true;
  document.getElementById("dob").readOnly=true;
  document.getElementById("address").readOnly=true;
  $("#home_pagediv").hide();
  $("#login_formdiv").hide();
  $("#allTripsTableDiv").hide();
  $("#newtrip_formdiv").hide();
  $("#profileSection").show();
}

//User Page Functions/ Ajax Window
function userHomePage() {
  loadjourney();
  $("#userRenewPage").hide();
  $("#bookTicketForm").hide();
  $("#paymentWindow").hide();
  $("#profileSection").hide();
  $("#userDashboard").show();
}
function renewPage() {
  $(".renewRadioClass").prop("checked", false);
  $("#bookTicketForm").hide();
  $("#userDashboard").hide();
  $("#paymentWindow").hide();
  $("#profileSection").hide();
  $("#userRenewPage").show();
}
function bookTicket() {
  loadtripsUser();
  $("#originDatalist").empty();
  $("#destinationDatalist").empty();
  $("#userRenewPage").hide();
  $("#userDashboard").hide();
  $("#paymentWindow").hide();
  $("#profileSection").hide();
  $("#bookTicketForm").show();
}

function profileSection() {
  getUserDetails();
  getProfileImage();
  document.getElementById("name").readOnly=true;
  document.getElementById("dob").readOnly=true;
  document.getElementById("address").readOnly=true;
  $("#bookTicketForm").hide();
  $("#userDashboard").hide();
  $("#paymentWindow").hide();
  $("#userRenewPage").hide();
  $("#profileSection").show();
}

//call this when document is ready
document.addEventListener("DOMContentLoaded", () => {
  loaduser();
  onLoad();
  loadjourney();
  getUsername();
  getProfileImage();
  $("#profileSection").hide();
  $("#paymentWindow").hide();
  $("#login_formdiv").hide();
  $("#newtrip_formdiv").hide();
  $("#allTripsTableDiv").hide();
  $("#home_pagediv").show();
  $("#userRenewPage").hide();
  $("#userDashboard").show();
  $("#bookTicketForm").hide();
});

// COMMON FUNCTIONS---------------------------------------->
//get current user's username
function getUsername() {
  const request = new XMLHttpRequest();
  request.open("GET", "http://localhost:8080/getuser");
  request.onload = () => {
    console.log(request.responseText);
    $("#loggedinuser").text(request.responseText);
  };
  request.send();
}

// upload profile image
async function uploadFile() {
  let formData = new FormData(); 
  formData.append("file", fileupload.files[0]);
  let response = await fetch('/upload', {
    method: "POST", 
    body: formData
  });
  if (response.status == 200) {
    alert("File successfully uploaded.");
    getProfileImage();
  }
} 

//get current user's profile image
function getProfileImage(){
  const request = new XMLHttpRequest();
  request.open("GET", "http://localhost:8080/image/getProfileimage");
  request.onload = () => {
    try {
      const json = JSON.parse(request.responseText);
      //console.log(json);
      document.getElementById("profileImageCanvas").src="http://localhost:8080/getimage/"+json["imageUrl"];
      document.getElementById("nav_bar_profile_pic").src="http://localhost:8080/getimage/"+json["imageUrl"];

    } catch (err) {
      console.log("cannot find profile Image for you!");
      console.log(err);
    }
  };
  request.send();
}

// Get current user's basic details
function getUserDetails(){
  const request = new XMLHttpRequest();
  request.open("GET", "http://localhost:8080/getBasicDetails");
  request.onload = () => {
    try {
      const json = JSON.parse(request.responseText);
      //console.log(json["name"]);
      document.getElementById("name").value=json["name"];
      document.getElementById("dob").value=json["dob"];
      document.getElementById("address").value=json["address"];
    } catch (err) {
      console.log("cannot find basic details for you!");
      console.log(err);
    }
  };
  request.send();
}

// Make basic profile details form editable
function makeEditable(){
  document.getElementById("name").readOnly=false;
  document.getElementById("dob").readOnly=false;
  document.getElementById("address").readOnly=false;
}

// Google Signout
function signOut() {
  var auth2 = gapi.auth2.getAuthInstance();
  auth2.signOut().then(function () {
    console.log("User signed out.");
    document.getElementById("navbtn4").click();
  });
}

// load google authentication manager in new web page
function onLoad() {
  gapi.load("auth2", function () {
    gapi.auth2.init();
  });
}


// POST/ Update basic details of the currently logged in user
$(document).ready(function () {
  $("#basicDetailBtn").click(function () {
    document.getElementById("msgBox1").style.display="none";
    document.getElementById("msgBox2").style.display="none";
    var name = $("#name").val();
    var dob = $("#dob").val();
    var address = $("#address").val();
    var profession = $("input:radio[name=rb2]:checked").val();
    var jsonData = {
      name: name,
      dob: dob,
      address: address,
      profession: profession,
    };
    console.log(jsonData);
    jsonData = JSON.stringify(jsonData);
    console.log(jsonData);

    const xhr = new XMLHttpRequest();
    xhr.open("POST", "http://localhost:8080/basicDetails");
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(jsonData);
    xhr.onreadystatechange=function(){
      if(this.readyState==4){
        if(this.status==200){
          document.getElementById("msgBox1").style.display="";
        }else{
          document.getElementById("msgBox2").style.display="";
        }
      }
    }
  });
});


//----------------> COMMON FUNCTION              ENDED----------<



//---------------------->ADMIN PAGES<-----------------------\\
// add new trip
$(document).ready(function () {
  $("#newtripsubmit").click(function () {
    document.getElementById("msgBoxTrip1").style.display="none";
    document.getElementById("msgBoxTrip2").style.display="none";

    var origin = $("#origin").val();
    var desti = $("#desti").val();
    var dist = $("#dist").val();
    var price = $("#price").val();

    var formData = {
      origin: origin,
      destination: desti,
      distance: dist,
      price: price,
    };
    formData = JSON.stringify(formData);
    const xhr = new XMLHttpRequest();
    xhr.open("POST", "http://localhost:8080/do_addtrip");
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(formData);
    xhr.onreadystatechange=function(){
      if(this.readyState==4){
        if(this.status==200){
          document.getElementById("msgBoxTrip1").style.display="";
        }else{
          document.getElementById("msgBoxTrip2").style.display="";
        }
      }
    }
  });
});





// Navigation Bar -> Admin Home Page
// Populate registered users table
const userTable = document.querySelector("#userTable>tbody");
function loaduser() {
  const request = new XMLHttpRequest();
  request.open("GET", "http://localhost:8080/listout1");
  request.onload = () => {
    try {
      const json = JSON.parse(request.responseText);
      console.log(json);
      populateUserTable(json);
    } catch (err) {
      console.log("cannot find users for you!");
      console.log(err);
    }
  };
  request.send();
}
var tb = document.getElementById("userTable");
function resetTable() {
  while (userTable.firstChild) {
    userTable.removeChild(userTable.firstChild);
  }
}
function populateUserTable(json) {
  while (userTable.firstChild) {
    userTable.removeChild(userTable.firstChild);
  }
  json.forEach((row) => {
    const tr = document.createElement("tr");
    //console.log(row["password"]);
    Object.keys(row).forEach(function (key) {
      if (key != "password" && key != "journey") {
        const td = document.createElement("td");
        td.textContent = row[key];
        tr.appendChild(td);
      }
    });
    userTable.appendChild(tr);
  });
}

// Populate "Available Trips" table
var tripsData;
const tripsTable = document.querySelector("#allTripsTable>tbody");
function loadtrips() {
  const request = new XMLHttpRequest();
  request.open("GET", "http://localhost:8080/alltrips");
  request.onload = () => {
    try {
      tripsData = JSON.parse(request.responseText);
      console.log(tripsData);
      populatetripstable(tripsData);
    } catch (err) {
      console.log("cannot find trips for you!");
      console.log(err);
    }
  };
  request.send();
}
var tb = document.getElementById("allTripsTable");
function resetTable() {
  while (tripsTable.firstChild) {
    tripsTable.removeChild(tripsTable.firstChild);
  }
}
function populatetripstable(json) {
  while (tripsTable.firstChild) {
    tripsTable.removeChild(tripsTable.firstChild);
  }
  json.forEach((row) => {
    const tr = document.createElement("tr");
    var selectorigin = $("#placeo");
    var selectdestination = $("#placed");
    selectorigin.append($("<option>", { text: row["origin"] }));
    selectdestination.append($("<option>", { text: row["destination"] }));
    Object.keys(row).forEach(function (key) {
      const td = document.createElement("td");
      td.textContent = row[key];
      tr.appendChild(td);
    });
    tripsTable.appendChild(tr);
  });
}

// ------------> FILTER FUNCTION
function resetTripsTable() {
  while (tripsTable.firstChild) {
    tripsTable.removeChild(tripsTable.firstChild);
  }
}
function checkDropDown() {
  var filterBy = $("input:radio[name=filterbyradio]:checked").val();
  if (filterBy === "origin") {
    console.log("origin selected");
    var originPlace = $("#placeo").val();
    console.log(originPlace);
    sortTripsTable(1, originPlace);
  } else {
    console.log("destination selected");
    var destinationPlace = $("#placed").val();
    console.log(destinationPlace);
    sortTripsTable(2, destinationPlace);
  }
}

function sortTripsTable(flag, place) {
  resetTripsTable();
  if (flag === 1) {
    tripsData.forEach((row) => {
      const tr = document.createElement("tr");
      if (row["origin"] === place) {
        Object.keys(row).forEach(function (key) {
          const td = document.createElement("td");
          td.textContent = row[key];
          tr.appendChild(td);
        });
        tripsTable.appendChild(tr);
      }
    });
  } else {
    tripsData.forEach((row) => {
      const tr = document.createElement("tr");
      if (row["destination"] === place) {
        Object.keys(row).forEach(function (key) {
          const td = document.createElement("td");
          td.textContent = row[key];
          tr.appendChild(td);
        });
        tripsTable.appendChild(tr);
      }
    });
  }
}
//---------------------------->FILTER FUNCTION OVER


//-----------------------------------> USER webpage ------------<

// get renewal status of current logged in customer
function getRenewalStatus() {
  const request = new XMLHttpRequest();
  request.open("GET", "http://localhost:8080/renewalstats");
  request.onload = () => {
    console.log(request.responseText);
    var days = parseInt(request.responseText);
    if (days < 0) {
      $("#passValidityLabel").text("0");
    } else {
      $("#passValidityLabel").text(request.responseText);
    }
  };
  request.send();
}

// user dashboard
const journeyHistory = document.querySelector("#userDashboardTable>tbody");
function loadjourney() {
  getRenewalStatus();
  const request = new XMLHttpRequest();
  request.open("GET", "http://localhost:8080/index1");
  request.onload = () => {
    try {
      const json = JSON.parse(request.responseText);
      console.log(json);
      populateJourneyTable(json);
    } catch (err) {
      console.log("cannot find journey history for you!");
      console.log(err);
    }
  };
  request.send();
}
var tb = document.getElementById("userDashboardTable");
function resetTable() {
  while (journeyHistory.firstChild) {
    journeyHistory.removeChild(journeyHistory.firstChild);
  }

  tb.style.display = "none";
}
function populateJourneyTable(json) {
  while (journeyHistory.firstChild) {
    journeyHistory.removeChild(journeyHistory.firstChild);
  }
  tb.style.display = "";
  json.forEach((row) => {
    const tr = document.createElement("tr");
    console.log(row);
    Object.keys(row).forEach(function (key) {
      const td = document.createElement("td");
      td.textContent = row[key];
      tr.appendChild(td);
    });
    journeyHistory.appendChild(tr);
  });
}


//  Populating Data list for BOOK TICKET PAGE
function loadtripsUser() {
  const request = new XMLHttpRequest();
  request.open("GET", "http://localhost:8080/alltrips");
  request.onload = () => {
    try {
      tripsDataTicketWindow = JSON.parse(request.responseText);
      populatetripstableUser(tripsDataTicketWindow);
    } catch (err) {
      console.log("cannot find trips for you!");
      console.log(err);
    }
  };
  request.send();
}
function populatetripstableUser(json) {
  json.forEach((row) => {
    var selectorigin = $("#originDatalist");
    var selectdestination = $("#destinationDatalist");
    selectorigin.append($("<option>", { text: row["origin"] }));
    selectdestination.append($("<option>", { text: row["destination"] }));
  });
}

// after successfull payment return back to USER Dashboard
function finalizePayment() {
  userHomePage();
  var formData = {
    dummyData: "Its a Dummy Data",
  };
  formData = JSON.stringify(formData);
  const xhr = new XMLHttpRequest();
  xhr.open("POST", "http://localhost:8080/payment");
  xhr.setRequestHeader("Content-Type", "application/json");
  xhr.send(formData);
}

//-------APYMENT FUNCTIONS
//payment global variables
var paymentFlag = 0; //1 for passrenewal | 2 for booking ticket
var formDataMonths = "";

// payment Services
function renewPassService() {
  console.log("Renew Pass Service is called  " + paymentFlag);
  renewPassFinal(formDataMonths);
  paymentFlag = 0;
}

function ticketBookingService() {
  if (paymentFlag === 2) {
    bookTicketFinal();
  }
  paymentFlag = 0;
}

// final payment calls

function renewPassFinal(totalMonths) {
  var formData = {
    months: totalMonths,
  };
  formData = JSON.stringify(formData);
  const xhr = new XMLHttpRequest();
  xhr.open("POST", "http://localhost:8080/renewpass");
  xhr.setRequestHeader("Content-Type", "application/json");
  xhr.send(formData);
}

function bookTicketFinal() {
  var origin = $("#origininput").val();
  var destination = $("#destinationinput").val();
  var bookingMsg = "Trip from (" + origin + ") to (" + destination + ")";
  paymentWindow(tripPrice, bookingMsg);
  var formData = {
    origin: origin,
    destination: destination,
  };
  formData = JSON.stringify(formData);
  const xhr = new XMLHttpRequest();
  xhr.open("POST", "http://localhost:8080/bookit");
  xhr.setRequestHeader("Content-Type", "application/json");
  xhr.send(formData);
  console.log();
}

//proceed with renewal option
function proceedRenew() {
  paymentFlag = 1;
  var renewOption = $("input:radio[name=renewradio1]:checked").val();
  if (renewOption === "six") {
    console.log("six");
    paymentWindow(1000, "Bus Pass 6 months");
    formDataMonths = "6";
  } else {
    console.log("twelve");
    paymentWindow(2000, "Bus Pass 12 months");
    formDataMonths = "12";
  }
}


var tripsDataTicketWindow;
var tripPrice, tripId;
function proceedBookticket() {
  paymentFlag = 2;
  var origin = $("#origininput").val();
  var destination = $("#destinationinput").val();
  tripPrice = checkTripValidity(tripsDataTicketWindow, origin, destination);
  if (tripPrice != 0) {
    document.getElementById("bookticketBtn").style.display = "";
    document.getElementById("priceLabel").innerHTML = tripPrice;
  } else {
    alert("This trip does not exist");
  }
}

// update BUS Ticket with new data after validation
var today = new Date();
var date = today.getFullYear()+'-'+(today.getMonth()+1)+'-'+today.getDate();
function checkTripValidity(tripsdata, start, end) {
  var flag = 0;
  tripsdata.forEach((row) => {
    if (row["origin"] === start && row["destination"] === end) {
      flag = row["price"];
      tripId = row["trip_id"];
      document.getElementById("passengerLabelT").innerHTML="Default Name";
      document.getElementById("originLabelT").innerHTML=row["origin"];
      document.getElementById("destinationLabelT").innerHTML=row["destination"];
      document.getElementById("distanceLabelT").innerHTML=row["distance"];
      document.getElementById("priceLabelT").innerHTML=row["price"];
      document.getElementById("tripIDLabel").innerHTML=row["trip_id"];
      document.getElementById("dateLabel").innerHTML=date;
    }
  });
  return flag;
}

// create PDF from HTML ()div="bus_ticket"
function CreatePDFfromHTML() {
  var HTML_Width = $(".custom_form_ticket").width();
  var HTML_Height = $(".custom_form_ticket").height();
  var top_left_margin = 15;
  var PDF_Width = HTML_Width + (top_left_margin * 2);
  var PDF_Height = (PDF_Width * 1.5) + (top_left_margin * 2);
  var canvas_image_width = HTML_Width;
  var canvas_image_height = HTML_Height;

  var totalPDFPages = Math.ceil(HTML_Height / PDF_Height) - 1;

  html2canvas($(".custom_form_ticket")[0]).then(function (canvas) {
      var imgData = canvas.toDataURL("image/jpeg", 1.0);
      var pdf = new jsPDF('p', 'pt', [PDF_Width, PDF_Height]);
      pdf.addImage(imgData, 'JPG', top_left_margin, top_left_margin, canvas_image_width, canvas_image_height);
      for (var i = 1; i <= totalPDFPages; i++) { 
          pdf.addPage(PDF_Width, PDF_Height);
          pdf.addImage(imgData, 'JPG', top_left_margin, -(PDF_Height*i)+(top_left_margin*4),canvas_image_width,canvas_image_height);
      }
      pdf.save("BusTicket"+date+".pdf");
  });
}

// PAyment window
function paymentWindow(amount, item) {
  $("#userRenewPage").hide();
  $("#bookTicketForm").hide();
  $("#paymentWindow").show();
  document.getElementById("amtLabel").innerHTML = parseInt(amount) + " INR";
  document.getElementById("itemLabel").innerHTML = item;
  document.getElementById("price").value = parseInt(amount) / 76.49;
  document.getElementById("description").value = item;
  document.getElementById("currency").value = "USD";
  document.getElementById("method").value = "paypal";
  document.getElementById("intent").value = "sale";
  return 1;
}


