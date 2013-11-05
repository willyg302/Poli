// Bootstrap 3.0.0 shiv for Meteor, since its default is 2.3.0

Package.describe({
	summary: "Load Bootstrap 3.0.0 script"
});

Package.on_use(function (api) {
	api.add_files(['../../public/bootstrap/js/bootstrap.min.js'], 'client');
});