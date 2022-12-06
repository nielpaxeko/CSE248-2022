//jshint esversion:6

const mongoose = require("mongoose");
mongoose.connect("mongodb://localhost:27017/fruitsDB", { useNewUrlParser: true});

// Schema for Fruit
const fruitSchema = new mongoose.Schema ({
  name: {
    type: String,
    required: [true, "Please enter a name"]
  },
  rating: {
    type: Number,
    min: 1,
    max: 100,
  },
  review: String
})
const Fruit = mongoose.model("Fruit", fruitSchema);

const fruit = new Fruit({
  name: "Apple",
  rating: 34,
  review: "Pretty solid fruit"
})
const peach = new Fruit({
  rating: 10,
  review: "It is ok"
})
//fruit.save();
/*
const kiwi = new Fruit({
  name: "Kiwi",
  rating: 10,
  review: "The best fruit"
})
const orange = new Fruit({
  name: "Orange",
  rating: 4,
  review: "Too sour"
})
const banana = new Fruit({
  name: "Banana",
  rating: 3,
  review: "Weird texture"
})
Fruit.insertMany([kiwi, orange, banana], function(err) {
  if (err) {
    console.log(err);
  } else {
    console.log("Fruits has been saved")
  }
})*/

// Person Schema
const personSchema = new mongoose.Schema ({
  name: String,
  age: Number,
  favoriteFruit: fruitSchema
})
const Person = mongoose.model("Person", personSchema)

const pineapple = new Fruit({
  name: "Pineapple",
  rating: 99,
  review: "Absolutely delicious"
})
const mango = new Fruit({
  name: "Mango",
  rating: 50,
  review: "kinda delicious"
})
//mango.save();
//pineapple.save();

const amy = new Person({
  name: "Amy",
  age: 12,
  favoriteFruit: pineapple
})

const jhon = new Person({
  name: "Jhon",
  age: 37,
})

//amy.save();
//jhon.save();

Fruit.find(function(err, fruits) {
  if (err) {
    console.log(err);
  } else {
    mongoose.connection.close();
    //console.log(fruits);
    fruits.forEach(function(fruit) {
      console.log(fruit.name);
    })
  }
})

Person.find(function(err, persons) {
  if (err) {
    console.log(err);
  } else {
    mongoose.connection.close();
    //console.log(persons);
    persons.forEach(function(person) {
      console.log(person.name);
    })
  }
})
/*Person.updateOne({name: "Jhon"}, {favoriteFruit:mango}, function(err) {
 if (err) {
    console.log(err);
  } else {
    console.log("Updated jhon");
  }
});
Person.deleteMany({name:"Jhon"}, function(err) {
  if (err) {
    console.log(err);
  } else {
    console.log("Deleted document");
  }
});*/
/*Fruit.updateOne({_id: "638e97fe8ffc497bb36c2d0a"}, {name:"Peach"}, function(err) {
  if (err) {
    console.log(err);
  } else {
    console.log("Successfully updated document")
  }
});

Fruit.deleteOne({_id:"638e8bef32fad8a8010f5114"}, function(err) {
  if (err) {
    console.log(err);
  } else {
   console.log("Deleted document");
  }
})*/


