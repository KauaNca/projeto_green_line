let count = 1;
document.getElementById("radio1").checked = true;

setInterval(function(){
    nextImage();
},3000)

function nextImage(){
    count++;
    if(count > 6){
        count = 1;
    }
    document.getElementById("radio" + count).checked = true;
}

const prev = document.getElementById('prev-btn')
const next = document.getElementById('next-btn')
const list = document.getElementById('item-list')

const itemWidth = 150
const padding = 10

prev.addEventListener('click',()=>{
  list.scrollLeft -= itemWidth + padding;
  if(list.scrollLeft <= 0){
    console.log(list.scrollLeft);
    prev.style.borderTopColor= "white";
    prev.style.borderLeftColor= "white";
    list.style.transition = "4s";
    next.style.borderTopColor = "black";
    next.style.borderRightColor = "black";
  }
})

next.addEventListener('click',()=>{
  list.scrollLeft += itemWidth + padding;
  if(list.scrollLeft >= (list.scrollWidth - list.clientWidth)){
    next.style.borderTopColor = "white";
    next.style.borderRightColor = "white";
    prev.style.borderTopColor= "black";
    prev.style.borderLeftColor= "black";
    console.log(list.scrollWidth);
  }
})