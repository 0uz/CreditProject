
function login(theUrl,identification,psw){
  var xhr = new XMLHttpRequest();
  xhr.onreadystatechange = function () {
    if (xhr.readyState === 4) {
      sessionStorage.setItem('identificationNo', identification)
      window.location.replace("./resultPage.html");
    }};
  xhr.open("POST", theUrl, true);
  xhr.setRequestHeader('Content-Type', 'application/json');

  xhr.send(JSON.stringify({
    identificationNo:identification,
    password:psw
  }));
}

window.addEventListener( "load", function () {
    function getData() {
      const FD = new FormData(form);
      var object = {};
      FD.forEach(function(value, key){
          object[key] = value;
      });
      return object;
    }
    
    // Access the form element...
    const form = document.getElementById( "querryForm" );
  
    // ...and take over its submit event.
    form.addEventListener( "submit", function ( event ) {
      event.preventDefault();
      var data = getData();
      login("http://127.0.0.1:8080/login",data.identificationNo,data.password);
    } );
  } );