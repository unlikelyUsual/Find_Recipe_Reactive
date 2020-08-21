$(document).ready(()=>{

    $('#addIngredient').on('click',(ev)=>{
        ev.preventDefault();
        const template = $('#ingredientTemplate').html();
        const container = $('#ingredientContainer');
        const range = $('#ingredientSize');
        let size = range.val() !== '' ? parseInt(range.val()) : 0 ;
        let string =template
            .replace(/{{amountName}}/g,"ingredients["+ size +"].amount")
            .replace(/{{uomName}}/g,"ingredients["+size +"].unitOfMeasure.description")
            .replace(/{{uomId}}/g,"ingredients["+size +"].unitOfMeasure.id")
            .replace(/{{descriptionName}}/g,"ingredients["+size +"].description");
        container.append(string);
        range.val(size++);

    });

    $('#uploadImage').on('click',(ev)=>{
       ev.preventDefault();
       $('#image').trigger('click');
    });

    const uploadImage = async (image) =>{
        const formData  = new FormData();
        formData.append("file",image);
        formData.append("recipe",$('#id').val());
        await fetch('/recipe/uploadImage',{
            method : 'POST',
            headers : {
                //'Content-Type': 'multipart/form-data'
            },
            body : formData
        });
    };

    $('#image').change(function (ev) {
        if(this.files.length > 0){
            uploadImage(this.files[0])
                .then(res=>{
                    window.alert("Uploaded");
                   window.location.reload()
                })
                .catch(err=>console.log(err));
        }
    });

      const searchRecipe = async (keyWord) =>{
           const req = await fetch('/recipe/search',{
                method : 'POST',
                headers : {
                  'Content-Type': 'application/json',
                  'Accept': 'application/json',
                },
                body : JSON.stringify({ 'description' : keyWord })
            });
            return await req.json();
        };

        const displayRecipes = (recipes)=>{
          const template = $('#recipeTemplate').html();
          const block = $('.recipe-block');
          const noDataBlock = $('.no-data');
          block.find('.card').remove();
          let appendCards = "";
          if(Array.isArray(recipes) && recipes.length !== 0){
          noDataBlock.hide();
          recipes.forEach(recipe=>{
             let src = "/images/1x/no-photo.png";
             if(recipe.imageString !== undefined && recipe.imageString != null && recipe.imageString !== ''){
                 src = "data:image/png;base64, " +recipe.imageString;
             }
             appendCards+= template
                           .replace(/{{src}}/g,src)
                           .replace(/{{description}}/g,recipe.description)
                           .replace(/{{content}}/g, recipe.directions.substring(0,160) + (recipe.directions.length > 160  ?  "..." : "" )   )
                           .replace(/{{editLink}}/g,"/recipe/"+recipe.id)
                           .replace(/{{viewLink}}/g,"/recipe/modify/" + recipe.id);
             });
          } else noDataBlock.show();
          block.append(appendCards);
        };

        $('#searchBtn').on('click',function (ev) {
           ev.preventDefault();
           const keyWord = $('#searchKeyField')
            if( keyWord.val() !== undefined){
                searchRecipe(keyWord.val())
                    .then(res=>{
                       //console.log(res);
                       displayRecipes(res);
                    })
                    .catch(err=>console.log(err));
            }
        });
});

function setId(select) {
    const $this = $(select);
    const id = $this.closest(".form-group").find('.uomId');
    id.val($this.find('option:selected').attr('id'));
}