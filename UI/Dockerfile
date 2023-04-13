FROM httpd:2.4-alpine
ARG testing=false
COPY ./site/ /usr/local/apache2/htdocs/
RUN if ${testing} ; then mv /usr/local/apache2/htdocs/test_data/fetch.js /usr/local/apache2/htdocs/fetch.js ; fi