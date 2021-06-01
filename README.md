repo-list

feature:repo-add mvn:br.com/gloop-features/0.0.1-SNAPSHOT/xml

feature:install gloop-jpa-datasource
feature:install gloop-jpa-provider-ds-hibernate
feature:install gloop-jpa-command
feature:install gloop-rest-whiteboard

repo-remove gloop-0.0.1-SNAPSHOT

feature:uninstall gloop-rest-whiteboard
feature:uninstall gloop-jpa-command
feature:uninstall gloop-jpa-provider-ds-hibernate
feature:uninstall gloop-jpa-datasource
