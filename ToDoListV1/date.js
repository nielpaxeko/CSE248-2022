module.exports.getDate = function() {
    let today= new Date();
    let options= {
    weekday: "long",
    month: "long",
    day: "numeric"
   }
   return today.toLocaleDateString("en", options)
}
module.exports.getDay = function() {
    let today= new Date();
    let options= {
    weekday: "long",
   
   }
   return today.toLocaleDateString("en", options)
}
