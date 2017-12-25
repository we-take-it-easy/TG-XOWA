(function (xo) {
  "use strict";
  xo.app.mode = 'http_server';
  xo.http_server = new function() {
    var timeout = 30000; // note that Safari is lowest with 30 seconds
    this.post_url = function() {
      return location.protocol + '//' + location.host + '/exec/json';  // EX:"http:" + "//" + "localhost:8080" + "/exec/json"
    }
    this.send_and_poll = function(msg_str) {      
      // start listening immediately in case send-msg-to-server returns too quickly
      this.poll();

      // do send-msg-to-server
      var xreq = new XMLHttpRequest();
      xreq.open('POST', this.post_url(), true);
      var form_data = new FormData();
      form_data.append('msg', msg_str);
      form_data.append('app_mode', 'http_server');
      xreq.send(form_data);
    }
    this.poll = function() {
      var xreq = new XMLHttpRequest();
      
      // set-up callback for timeout
      xreq.timeout = timeout;
      xreq.ontimeout = function () {
        console.log('timed out');
        xo.http_server.poll();   // RECURSION: previous long-polling timed-out; start another long-poll
      }

      // set-up callback for success
      xreq.onreadystatechange = function() {
        if (xreq.readyState == 4 && xreq.status == 200) {
          var response = xreq.responseText;
          console.log(response);
          if (response.startsWith('long-poll ignored:')) {
            console.log(response);
            // previous long-polling was cancelled by another long-poll; do not start another long-poll
            // xo.http_server.poll(); // RECURSION
          }  // do nothing; already polling
          else {
            console.log('received response');
            eval(response);
            xo.http_server.poll(); // RECURSION: previous long-polling ended without response; start another long-poll
          }
        }
      };

      // build msg
      var form_data = new FormData();
      form_data.append
      ( 'msg'
      , '{ "cmd":"long_poll"'
      + ', "data":'
      + '  { "timeout":"' + timeout + '"'
      + '  , "guid":"' + generateUUID() + '"}}');  // NOTE: XOWA http_server uses long_poll cmd to loop in separate thread
      form_data.append('app_mode', 'http_server');

      // send-msg-to-server
      xreq.open('POST', this.post_url(), true);      
      xreq.send(form_data);            
    }    
    
    // https://stackoverflow.com/questions/105034/create-guid-uuid-in-javascript
    function generateUUID () { // Public Domain/MIT
      var d = new Date().getTime();
      if (typeof performance !== 'undefined' && typeof performance.now === 'function'){
          d += performance.now(); //use high-precision timer if available
      }
      return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
          var r = (d + Math.random() * 16) % 16 | 0;
          d = Math.floor(d / 16);
          return (c === 'x' ? r : (r & 0x3 | 0x8)).toString(16);
      });
    }
  }
}(window.xo = window.xo || {}));
