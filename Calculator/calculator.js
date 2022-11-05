const express = require("express");
const bodyParser =require("body-parser");
const app = express();
// Get values from user
app.use(express.urlencoded({extended:true}));
// Main branch is index
app.get("/", function(req, res) {
    res.sendFile(__dirname + "/index.html");
});
app.post("/", function(req, res) {
    var num1 = Number(req.body.num1);
    var num2 = Number(req.body.num2);
    var result = num1+num2;
    res.send("The result of the calculation is: " + result);
});
// BMI branch
app.get("/bmiCalculator", (req, res) => { 
    res.sendFile(__dirname + "/bmiCalculator.html");
});
 
app.post("/bmiCalculator", (req, res) => {
    var Weight = parseFloat(req.body.Weight);
    var Height = parseFloat(req.body.Height);
    var bmi = Math.round(Weight / (Height * Height));
    res.send("Your BMI Calculation is " + bmi);
});
app.listen(3000, function() {
    console.log("Server is running in port 3000");
});