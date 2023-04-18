ARG mode='prod'

FROM httpd:2.4-alpine AS base
COPY ./site/index.html ./site/meowmed.css ./site/src.js ./site/favicon.ico /usr/local/apache2/htdocs/
COPY ./site/framework /usr/local/apache2/htdocs/framework
COPY ./site/assets /usr/local/apache2/htdocs/assets
COPY ./httpd.conf /usr/local/apache2/conf/
COPY ./cors.conf /usr/local/apache2/conf/extra/

FROM base AS meowmed-ui-test
COPY ./site/fetch.js /usr/local/apache2/htdocs/
COPY ./site/test_data /usr/local/apache2/htdocs/test_data

FROM base AS meowmed-ui-prod
ARG customerService
ARG policyService
COPY ./site/fetch_prod.js /usr/local/apache2/htdocs/fetch.js
RUN sed -i s~\${customerService}~$customerService~ /usr/local/apache2/htdocs/fetch.js
RUN sed -i s~\${policyService}~$policyService~ /usr/local/apache2/htdocs/fetch.js

FROM meowmed-ui-${mode} AS meowmed-ui
