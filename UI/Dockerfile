FROM httpd:2.4-alpine
ARG testing=false
COPY ./site/ /usr/local/apache2/htdocs/
RUN if ! ${testing} ; then rm /usr/local/apache2/htdocs/fetch.js ; mv /usr/local/apache2/htdocs/fetch_prod.js /usr/local/apache2/htdocs/fetch.js ; rm -r /usr/local/apache2/htdocs/test_data ; else rm /usr/local/apache2/htdocs/fetch_prod.js ; fi