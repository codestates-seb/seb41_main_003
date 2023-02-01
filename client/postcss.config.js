const postcssNormalize = require('postcss-normalize');
const atImport = require('postcss-import');

module.exports = {
  plugins: [
    require('stylelint'),
    require('autoprefixer'),
    require('postcss-simple-vars'),
    require('postcss-nested'),
    require('postcss-flexbugs-fixes'),
    require('postcss-preset-env')({
      autoprefixer: {
        flexbox: 'no-2009',
      },
      stage: 0,
    }),
    postcssNormalize(),
    atImport(),
  ],
};
