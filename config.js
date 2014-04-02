
{
	"port" : 2222,
	"hosts" : [{
		"name" : "mon-site.com",
		"document_root" : "C:/var/www/site1.com",
		"handlers" : [ {
			"clazz" : "ProxyHandler",
			"pattern" : "^.*$"
		}
		]
	}]
}