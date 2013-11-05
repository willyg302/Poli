// Moment.js for Meteor

Package.describe({
	summary: "A JavaScript date library for manipulating dates"
});

Package.on_use(function (api) {
	if (api.export) {
		api.export('moment');
	}
	api.add_files('moment.min.js', 'client');
	api.add_files('export-moment.js', 'client');
});