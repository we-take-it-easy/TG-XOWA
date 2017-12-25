// NOTE: edited from original to use local json libraries; DATE:2015-09-05
(function($){
if (window.xtn__graph__exec == null) {
  window.xtn__graph__exec = graph_exec;
}
function graph_exec() {
  var $content = $('#content');
	$content.find( '.mw-wiki-graph' ).each( function () {
    var graphId = $( this ).data( 'graph-id' );
    var elem = this;
    var spec = JSON.parse($(this).html());
    $(this).html('');
    vg.parse.spec
    ( spec, function(chart) 
      {
        if (chart) {
          chart({ el: elem}).update();
        }
      }
    );
  });
}
})(jQuery);