var express = require('express');
var router = express.Router();

var aptoObject = {
    luzBano: true,
    luzCocina: true,
    luzHabitacion1: true,
    luzHabitacion2: true,
    luzHabitacion3: true,
    luzHabitacion4: true
}

/* GET apto status. */
router.get('/aptoStatus', function(req, res, next) {  
  res.json(aptoObject);
    //res.send('respond with a resource');
});

/* POST Apto change light status  */
router.post('/changeAptoStatus', function(request, response) {  
    console.log ('REQUEST:', request.body.aptoInfo);
    var query1= request.query.aptoInfo;
    aptoObject = JSON.parse(query1);
    console.log ('QUERY', query1);
    response.send ('UPDATED INFO');
  });

/* POST Apto change light status  */
router.post('/changeAptoStatusBody', function(request, response) {  
    console.log ('REQUEST:', request.body);
    var query1= request.body;
    aptoObject = query1;
    console.log ('QUERY', query1);
    response.send ('UPDATED INFO');
  });


module.exports = router;
