package io.maksymuimanov.atiperatask;

final class TestConstants {
    public static final String FOUR_REPOS_WITH_FORKS_RESPONSE = """
			[
			  {
			    "id": 100001,
			    "node_id": "MDEwOlJlcG9zaXRvcnkxMDAwMDE=",
			    "name": "service-api",
			    "full_name": "example/service-api",
			    "private": false,
			    "owner": {
			      "login": "example",
			      "id": 1,
			      "node_id": "MDQ6VXNlcjE="
			    },
			    "html_url": "/example/service-api",
			    "description": "Main backend service",
			    "fork": false
			  },
			  {
			    "id": 100002,
			    "node_id": "MDEwOlJlcG9zaXRvcnkxMDAwMDI=",
			    "name": "service-api-fork",
			    "full_name": "example/service-api-fork",
			    "private": false,
			    "owner": {
			      "login": "example",
			      "id": 1,
			      "node_id": "MDQ6VXNlcjE="
			    },
			    "html_url": "/example/service-api-fork",
			    "description": "Forked repository",
			    "fork": true
			  },
			  {
			    "id": 100005,
			    "node_id": "QDEwOlJ2cG9zaXRvcnkxMDAwMDE=",
			    "name": "service-api-fork2",
			    "full_name": "example/service-api-fork2",
			    "private": false,
			    "owner": {
			      "login": "example",
			      "id": 1,
			      "node_id": "MDQ6VXNlcjE="
			    },
			    "html_url": "/example/service-api-fork2",
			    "description": "Forked 2 repository",
			    "fork": true
			  },
			  {
			    "id": 100024,
			    "node_id": "AQEEwOEEEcG9zaXRvckxMDAwMDE=",
			    "name": "smth",
			    "full_name": "example/smth",
			    "private": false,
			    "owner": {
			      "login": "example",
			      "id": 3,
			      "node_id": "MDQ6VXNlcjE="
			    },
			    "html_url": "/example/smth",
			    "description": "Another repository",
			    "fork": false
			  }
			]
			""";
    public static final String TWO_BRANCHES_RESPONSE = """
			[
			   {
			     "name": "main",
			     "commit": {
			       "sha": "ffffffffffffffffffffffffffffffffffffffff",
			       "url": "/repos/example/service-api-fork/commits/ffffffffffffffffffffffffffffffffffffffff"
			     },
			     "protected": false
			   },
			   {
				   "name": "develop",
				   "commit": {
					   "sha": "f1ffffffffffffffffffffffffffffffffffffff",
					   "url": "/repos/example/service-api-fork/commits/ffffffffffffffffffffffffffffffffffffffff"
				   },
				   "protected": false
			   }
			]
			""";
    public static final String ONE_BRANCH_RESPONSE = """
			[
			   {
			     "name": "main",
			     "commit": {
			       "sha": "ffffffffffffffffffffffffffffffffffffffff",
			       "url": "/repos/example/smth/commits/ffffffffffffffffffffffffffffffffffffffff"
			     },
			     "protected": false
			   }
			]
			""";
    public static final String API_ENDPOINT = "/api/v1.0/repos/example";

    private TestConstants() {
    }
}
