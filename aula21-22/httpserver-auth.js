const express = require('express');
const app = express();

// many of the followin parsing tecnhiques could be simplifed by using the middleware cookie-parser
// const cookieParser = require('cookie-parser')

const port = 3001;

app.get('/protectedresource', (req, resp) => {
    console.log(req.headers)
    if (req.header('Authorization')) {
        // TODO: check credentials
        console.log('Header authorization is present: ' + req.header('Authorization'));
        resp.sendStatus(200);
    } else {
        console.log('Requesting authorization header'); 
        resp.header(
            'WWW-Authenticate', 
            'Basic realm=testrealm')
        resp.sendStatus(401);  
    }
})

app.get('/printcookies', (req, resp) => {
    resp.statusCode = 200;
    resp.send(req.header('Cookie')+'<br>'+req.cookies);
})

app.get('/setcookies', (req, resp) => {
    resp.statusCode = 200;
    // eq. Set-Cookie: C1=A; C2=B; Path=/
    resp.setHeader('Set-Cookie', ['C1=A', 'C2=B']);
    resp.cookie('mycookie', 'some-value', { expires: new Date(Date.now() + 900000), httpOnly: true });
    resp.end();
})

const crypto = require('crypto');

app.get('/setcookies-hmac', (req, resp) => {
    const hmac = crypto.createHmac('sha256', 'changeit');

    resp.statusCode = 200;
    // TODO: random user id
    const id = 'random id';
    // compute hmac 
    const h = hmac.update(id).digest('hex');
    console.log(h);

    resp.setHeader('Set-Cookie', ['HCookie='+id, 'T='+h]);
    resp.cookie('HCookie2', id+':'+h, { expires: new Date(Date.now() + 900000), httpOnly: true });
    resp.send('cookie protected by HMAC');
})

app.get('/checkcookies-hmac', (req, resp) => {
    const hmac = crypto.createHmac('sha256', 'changeit');

    const t1 = getAppCookies(req, resp)['T'];
    const id = getAppCookies(req, resp)['HCookie'];
    const t2 = hmac.update(id).digest('hex');
    console.log(id);

    console.log(t1);
    console.log(t2);
    if (t1 == t2) {
        resp.statusCode = 200;
        resp.send('Cookies are valid');
    } else {
        resp.statusCode = 401;
        resp.send('Cookies hmac do not match');
    }   
})

// returns an object with the cookies' name as keys
const getAppCookies = (req) => {
    // extract the raw cookies from the request headers
    const rawCookies = req.headers.cookie.split('; ');

    const parsedCookies = {};
    rawCookies.forEach(rawCookie=>{
        const parsedCookie = rawCookie.split('=');
        parsedCookies[parsedCookie[0]] = parsedCookie[1];
    });
    return parsedCookies;
};
   

app.listen(port, (err) => {
    if (err) {
        return console.log('something bad happened', err);
    }
    console.log(`server is listening on ${port}`);
})