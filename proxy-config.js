
{
	"hosts" : [{
		"host" : "mon-site.com",
		"lb" : "roundrobin",
		"workers" : [
		           "127.0.0.1:1234",
		           "77.238.178.122:80"
		          ],
		"headers" : {
			"incoming": {
				"add" : [
				         {"My-ProxyHeader" : "123"},				         
				         {"AAAMy-ProxyHeader" : "123"},
				         {"BBBMy-ProxyHeader" : "123"}				         
				         ]
			},
			"outgoing" : { 
				"add" : [{"For-Client-Header" : "765"}]
			}
		}
	}]
}