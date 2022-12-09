//jshint esversion:6

const express = require("express");
const bodyParser = require("body-parser");
const ejs = require("ejs");
const mongoose = require("mongoose");

const app = express();

app.set("view engine", "ejs");
app.use(express.urlencoded({ extended: true }));
app.use(express.static("public"));

// Database
mongoose.connect("mongodb://localhost:27017/wikiDB", { useNewUrlParser: true });
const articleSchema = {
  title: String,
  content: String,
};
const Article = mongoose.model("Article", articleSchema);
// Requests targetting all articles

app.route("/articles")
  .get(function (req, res) {
    Article.find(function (err, foundArticles) {
      if (!err) {
        res.send(foundArticles);
      } else {
        res.send(err);
      }
    });
  })
  .post(function (req, res) {
    const newArticle = new Article({
      title: req.body.title,
      content: req.body.content,
    });
    newArticle.save(function (err) {
      if (!err) {
        res.send("Added new article");
      } else {
        res.send(err);
      }
    });
  })
  .delete(function (req, res) {
    Article.deleteMany(function (err) {
      if (!err) {
        res.send("Deleted");
      } else {
        res.send(err);
      }
    });
  });

// Requests targetting a specific articles

app.route("/articles/:articleTitle")
    .get(function() {
        Article.findOne({title: req.params.articleTitle}, function(err, foundArticle) {
            if (!err) {
                res.send(foundArticle);
            } else {
                res.send(err);
            }
        })
    })
    .put(function(req, res) {
        Article.updateOne(
            {title:req.params.articleTitle},
            {title: req.body.title, content: req.body.content},
            {overwrite: true},
            function(err, results) {
                if (!err) {
                    res.send("Updated article");
                } else {
                    res.send(err);
                }
            }
        ) 
    })
    .patch(function(req, res) {
       Article.updateOne(
        {title:req.params.articleTitle},
        {$set: req.body},
       )
    })
    .delete(function(req, res) {
        Article.deleteOne( 
        {title:req.params.articleTitle},
        function(err) {
            if (!err) {
                res.send("Deleted article");
            } else {
                res.send(err);
            }
        }
    )
});

app.listen(3000, function () {
  console.log("Server started on port 3000");
});
