console.log("hello world");
const tripstable = document.querySelector("#trips_table2>tbody");
function loadtrips() {
  const request = new XMLHttpRequest();
  request.open("GET", "http://localhost:8080/listout1");
  request.onload = () => {
    try {
      //console.log(request.responseText);
      const json = JSON.parse(request.responseText);
      populatetable(json);
    } catch (err) {
      console.log("canot find trips for you!");
      console.log(err);
    }
  };
  request.send();
}
var tb = document.getElementById("trips_table2");
function resetTable() {
  while (tripstable.firstChild) {
    tripstable.removeChild(tripstable.firstChild);
  }

  tb.style.display = "none";
}

function populatetable(json) {
  //console.log(json);
  while (tripstable.firstChild) {
    tripstable.removeChild(tripstable.firstChild);
  }
  tb.style.display = "";
  json.forEach((row) => {
    const tr = document.createElement("tr");
    console.log(row);
    Object.keys(row).forEach(function (key) {
      const td = document.createElement("td");
      td.textContent = row[key];
      tr.appendChild(td);
      //console.log(row[key]);
    });
    tripstable.appendChild(tr);
  });
}

document.addEventListener("DOMContentLoaded", () => {
  loadtrips();
});
console.log(tripstable);

$(document).ready(function () {
  $("#btnsubmit").click(function () {
    var username = $("#username").val();
    var password = $("#password").val();
    var jsonData = { uname: username, pwd: password };
    jsonData = JSON.stringify(jsonData);
    console.log(jsonData);

    const xhr = new XMLHttpRequest();
    xhr.open("POST", "http://localhost:8080/login1");
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(jsonData);
  });
});

var newtripformobj = document.getElementById("newtripform");
newtripformobj.style.display = "none";
function unhideForm(){
    newtripformobj.style.display = "";
}
$(document).ready(function () {
  $("#newtripsubmit").click(function () {
      unhideForm();
    
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
    console.log(formData);

    const xhr = new XMLHttpRequest();
    xhr.open("POST", "http://localhost:8080/do_addtrip1");
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(formData);
  });
});
