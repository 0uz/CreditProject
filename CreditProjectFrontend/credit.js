var result = JSON.parse(httpGet("http://127.0.0.1:8080/credit?identificationNo="+sessionStorage.getItem('identificationNo')));
document.getElementById("resultTextStatus").innerHTML = "Your credit: "+result.status;
document.getElementById("resultTextLimit").innerHTML = "Credit Limit: "+ result.creditLimit;
const btn = document.querySelector('button');
if(result.status == "APPROVED") document.getElementById("applyCreditBtn").style.display = "none";

function httpGet(theUrl){
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "GET", theUrl, false );
    xmlHttp.setRequestHeader("Authorization","Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMzE5Mjc5NjY4MiJ9.ogit1YZGgT616n6mOhjDPM-bpQwslLgM6JZ_IM4wYvaL0j4qeuhMOlAlMFSMZJLoSVb6x0MAGTvA8UIEd2cfOQ")
    xmlHttp.send( null );
    return xmlHttp.responseText;
}

function applyCredit(){
    var xhr = new XMLHttpRequest();
    xhr.open("POST", theUrl, true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.setRequestHeader("Authorization","Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMzE5Mjc5NjY4MiJ9.ogit1YZGgT616n6mOhjDPM-bpQwslLgM6JZ_IM4wYvaL0j4qeuhMOlAlMFSMZJLoSVb6x0MAGTvA8UIEd2cfOQ");
    xhr.send();
  }
  
