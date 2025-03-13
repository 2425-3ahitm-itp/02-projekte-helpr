set -e

cd hugo

docker run --rm \
  --name helpr \
  -v ${PWD}:/src \
  -p 1313:1313 \
  hugomods/hugo:exts-non-root "$@" --noBuildLock