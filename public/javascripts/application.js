$(function() {
  SyntaxHighlighter.all();
  $(".toggle a").click(function(e) {
    $(this).parents(".toggle").children().toggle();
  });
});
