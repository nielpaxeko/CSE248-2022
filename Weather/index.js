//weather in suffolk
weather("New York");
//Weather in desired location
$("button").click(function () {
  $(".card").slideUp();
  $(".card").slideDown();
  setTimeout(() => {
    weather($(".input_text").val())
  }, 500);
  
});

function weather(city) {
  fetch(
    "https://api.openweathermap.org/data/2.5/weather?q=" +
      city +
      "&units=metric&appid=af63f1ff09a88fd54bcae5db220ef89a"
  )
    .then((response) => response.json())
    .then((data) => {
      $("h1").text(data["name"]);
      //Display today's weather
      $("h4").text("Feels like - " + data["main"]["feels_like"] + "°C");
      $(".desc").text("Desc - " + data["weather"][0]["description"]);
      $(".temp").text("Temp - " + data["main"]["temp"] + "°C");
      $(".min").text("Min Temp - " + data["main"]["temp_min"] + "°C");
      $(".max").text("Max Temp - " + data["main"]["temp_max"] + "°C");
      $(".humidity").text("Humidity - " + data["main"]["temp"]);
      var timezone = data["timezone"];
      time(timezone);
      //$("h2").text("Time - " + data["timezone"]);
    })
    .catch((error) => {
      alert("city name not found");
    });
}
function time(timezone) {
  d = new Date();
  localTime = d.getTime();
  localOffset = d.getTimezoneOffset() * 60000;
  utc = localTime + localOffset;
  var target = utc + 1000 * timezone;
  nd = new Date(target);
  var month = nd.getMonth() +1;
  var day = nd.getDate();
  $("h2").text("Time: "  + day + "/" + month + "/" + nd.getFullYear() + ", " + nd.getHours() + ":" + nd.getMinutes());
} 
