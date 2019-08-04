const path = require('path');
const webpack = require('webpack');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const VueLoaderPlugin = require('vue-loader/lib/plugin');


module.exports = {
	mode: 'production',
	entry: {
		login:'./src/login.js',
		dogmarket: './src/dogmarket.js'},

	output: {
		filename: '[name].bundle.js',
		path: path.resolve(__dirname, 'dist'),
	},

	plugins: [
		new webpack.ProgressPlugin(),
		new HtmlWebpackPlugin({
			filename: "login.html",
			minify: {
				collapseWhitespace:true,
				removeComments:true,
			},
			hash:true,
			title: 'Login',
			template: "./src/login.html",
			chunks: ['login']
		}),
		new HtmlWebpackPlugin({
			filename: "dogmarket.html",
			minify: {
				collapaseWhitespace:true,
				removeComments:true,
			},
			hash: true,
			title:'Dogmarket',
			template: "./src/dogmarket.html",
			chunks: ['dogmarket']
		}),
		new VueLoaderPlugin(),
	],

	module: {
		rules: [
			{
				test:/.css$/,use:['style-loader','css-loader']
			},
			{test:/\.(png|jpg|svg|gif|jpeg)$/ ,
				use:'url-loader'
			},
			{test:/\.vue/,use:['vue-loader']},
			{
				test: /.(js|jsx)$/,
				include: [path.resolve(__dirname, 'src')],
				loader: 'babel-loader',
				options: {
					plugins: ['syntax-dynamic-import'],

					presets: [
						[
							'@babel/preset-env',
							{
								modules: false
							}
						]
					]
				}
			}
		]
	},

	optimization: {
		splitChunks: {
			cacheGroups: {
				vendors: {
					priority: -10,
					test: /[\\/]node_modules[\\/]/
				}
			},

			chunks: 'async',
			minChunks: 1,
			minSize: 30000,
			name: true
		}
	},

	devServer: {
		open: true
	},
	resolve: {
		alias: {
			vue : 'vue/dist/vue.esm.js'
		}
	}
};
