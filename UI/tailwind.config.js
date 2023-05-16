/** @type {import('tailwindcss').Config} */
import daisyui from "daisyui";
export default {
  content: [
    "./components/**/*.{jsx,tsx}",
    "./routes/**/*.{jsx,tsx}",
    "./islands/**/*.{jsx,tsx}",
  ],
  theme: {
    extend: {
      backgroundImage: {
        "search": "url('/search.svg')",
      },
    },
  },
  plugins: [daisyui],
};
