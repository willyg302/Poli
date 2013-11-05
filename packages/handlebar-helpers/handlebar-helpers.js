// Helper scope
if (typeof Helpers === 'undefined') {
	Helpers = {};
}

if (typeof Handlebars !== 'undefined') {
	Handlebars.registerHelper('comp', function (v1, operator, v2) {
		var isTrue = false;
		switch (operator) {
			case '===': isTrue = v1 === v2; break;
			case '!==': isTrue = v1 !== v2; break;
			case '<': isTrue = v1 < v2; break;
			case '<=': isTrue = v1 <= v2; break;
			case '>': isTrue = v1 > v2; break;
			case '>=': isTrue = v1 >= v2; break;
			case '||': isTrue = v1 || v2; break;
			case '&&': isTrue = v1 && v2; break;
		}
		return isTrue;
		//return isTrue ? options.fn(this) : options.inverse(this);
	});
}