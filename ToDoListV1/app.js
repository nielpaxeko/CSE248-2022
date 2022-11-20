const express = require("express");
const bodyParser = require("body-parser");
const date = require(__dirname + "/date.js");
const app = express();
const items = ["Buy Food", "Cook Food", "Eat food"];
const workItems = [];
app.set("view engine", "ejs");
app.use(bodyParser.urlencoded({extended: true}))
app.use(express.static("public"))

app.get("/", function(req, res) {
    let day = date.getDate();
    res.render("list",  {listTitle: day, newListItems: items});
});
app.post("/", function(req, res) {
    const item = req.body.newItem;
    items.push(item);
    res.redirect("/");
});

app.get("/about", function(req, res) {
    let item = req.body.newItem;
    workItems.push(item);
    res.redirect("/about");
});


app.listen(3000, function() {
    console.log("Server started on port 3000");
})