use denia;

-- Test 1:
insert into User (id,name,password) values(1,'Platon','e2baf3f5223080b8067f5af7843427cc');

insert into Page (id,pageName,description,email) 
	values (1,'Platon','Academia de filosofía','agustinmiquel@yahoo.es');

insert into Head (id,title,description,indexed,facebook,twitter,twitterTag,page_id) 
	values (1,'Lorem ipsum dolor sit amet','In utinam postulant duo, et quo feugait iudicabit.',0,1,1,'@lorem',1);
update Page set head_id = 1 where id = 1;

insert into Body (id,h1,h2,showSections,showContact,showMap,page_id) 
	values(1,'In utinam postulant duo, et quo feugait iudicabit.','¡Est te etiam quando!',1,1,1,1); 
update Page set body_id = 1 where id = 1;

insert into Address(id,name,street,town,postalCode,phone,page_id)
	values(1,'Academia Platon','C/ Diana nº 68','Denia','03700','666555444',1);
update Page set address_id = 1 where id = 1;

insert into Section (id,title,content,showSection,showInMenu,page_id)
	values(2,'Servicios','Nonumes fierent pericula sed eu. Verear sapientem gloriatur ad his, ut usu quidam impetus, sit an sapientem deseruisse sententiae. Ipsum virtute voluptatibus ei duo. Ubique facete expetendis vim eu.<br/><br/>Est volutpat consequat scripserit ei. Usu ei timeam prompta saperet. Mea cu lobortis laboramus suscipiantur. Case tempor qualisque eam ea, an nec sale novum, ex vel prompta consulatu consequat.',1,1,1);

insert into Section (id,title,content,showSection,showInMenu,page_id)
	values(3,'Galería','<img src="/denia/resources/pages/1/img1.jpg" width="200" height="100"><img src="/denia/resources/pages/1/img2.jpg" width="200" height="100"><img src="/denia/resources/pages/1/img3.jpg" width="200" height="100"><img src="/denia/resources/pages/1/img4.jpg" width="200" height="100">',1,1,1);

insert into Section (id,title,content,showSection,showInMenu,page_id)
	values(4,'Enlaces','<ul><li>* <a href="http://generator.lorem-ipsum.info/">Professional lorem ipsum generator for typographers.</a></li><li>* <a href="https://en.wikipedia.org/wiki/Lorem_ipsum">Lorem Ipsum - Wikipedia</a></li></ul>',1,1,1);

insert into Contact (id,contactDate,name,email,phone,description,userName,sentTo,result,page_id)
	values(1, '2017/02/16', 'Test', 'test@test.es', '123456789', 'Esto es una prueba', 'anonymous', 'agustinmiquel@yahoo.es', 'Ok', 1);
	
-- Test 2
insert into User (id,name,password) values(2,'Hipocrates','52b94380ffdd7d784861091631d55fe6');

insert into Page (id,pageName,description,email) 
	values (2,'Hipocrates','Farmacia y parafarmacia','');

insert into Head (id,title,description,indexed,facebook,twitter,twitterTag,page_id) 
	values (2,'The title','The description',0,0,0,'@lorem',2);
update Page set head_id = 2 where id = 2;

insert into Body (id,h1,h2,showSections,showContact,showMap,page_id) 
	values(2,'First header','Second header',1,0,0,2); 
update Page set body_id = 2 where id = 2;

insert into Address(id,name,street,town,postalCode,phone,page_id)
	values(2,'Farmacia Hipocrates','Carrer del Mar nº 1','Denia','03700','6655443322',2);
update Page set address_id = 2 where id = 2;

insert into Section (id,title,content,showSection,showInMenu,page_id)
	values(5,'Servicios','Nonumes fierent pericula sed eu. Verear sapientem gloriatur ad his, ut usu quidam impetus, sit an sapientem deseruisse sententiae. Ipsum virtute voluptatibus ei duo. Ubique facete expetendis vim eu.<br/><br/>Est volutpat consequat scripserit ei. Usu ei timeam prompta saperet. Mea cu lobortis laboramus suscipiantur. Case tempor qualisque eam ea, an nec sale novum, ex vel prompta consulatu consequat.',1,1,1);
