(function (){
  // Apply saved theme early if possible
  try {
    var t = localStorage.getItem('theme') || 'dark';
    document.documentElement.classList.add('theme-' + t);
  } catch(e) { /* ignore */ }

  // DOM ready handler
  function ready(fn){
    if(document.readyState!='loading') fn();
    else document.addEventListener('DOMContentLoaded', fn);
  }

  ready(function(){
    var btn = document.getElementById('theme-toggle');
    function current(){ return localStorage.getItem('theme') || 'dark'; }
    function updateButton(){
      if(!btn) return;
      var t = current();
      if(t === 'dark'){
        btn.textContent = 'Modo Escuro';
        btn.setAttribute('aria-pressed', 'true');
      } else {
        btn.textContent = 'Modo Claro';
        btn.setAttribute('aria-pressed', 'false');
      }
    }
    if(btn){
      btn.addEventListener('click', function(){
        var t = current();
        var next = (t === 'dark') ? 'light' : 'dark';
        try{
          localStorage.setItem('theme', next);
        }catch(e){}
        document.documentElement.classList.remove('theme-' + t);
        document.documentElement.classList.add('theme-' + next);
        updateButton();
      });
    }
    updateButton();
  });
})();

