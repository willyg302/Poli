// Helpful Handlebar helpers!

Package.describe({
	summary: "Handlebar helpers",
	//internal: true
});

Package.on_use(function (api) {
	api.use(['handlebars', 'deps'], 'client');
	api.export && api.export('Helpers');
	api.add_files(['handlebar-helpers.js'], 'client');
});