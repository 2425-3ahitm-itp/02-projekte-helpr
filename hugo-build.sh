set -e

cd hugo

docker run --rm \
  --name helpr \
  -v ${PWD}:/src \
  -v ${HOME}/hugo_cache:/tmp/hugo_cache \
  hugomods/hugo:exts-non-root \
  build