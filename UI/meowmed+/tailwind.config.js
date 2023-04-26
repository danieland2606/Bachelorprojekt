/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./components/**/*.{jsx,tsx}",
    "./routes/**/*.{jsx,tsx}",
    "./islands/**/*.{jsx,tsx}",
  ],
  theme: {
    extend: {},
  },
  plugins: [require("daisyui")],
};
