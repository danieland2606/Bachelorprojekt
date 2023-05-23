import { asset, Head } from "$fresh/runtime.ts";
import { AppProps } from "$fresh/src/server/types.ts";

export default function App({ Component }: AppProps) {
  const logo = " bg-right-top bg-no-repeat bg-15 md:bg-20 xl:bg-22";
  return (
    <>
      <Head>
        <title>Meowmed+</title>
        <meta
          name="description"
          content="MeowMed+ customer management application"
        />
        <meta name="author" content="Julian Opitz" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link rel="stylesheet" href={asset("/meowmed.css")} />
        <link rel="icon" type="image/svg+xml" href={asset("/favicon.svg")} />
      </Head>
      <div
        data-theme="retro"
        class="absolute w-full min-w-full min-h-full"
      >
        <div
          class={"p-5 pt-6 max-w-screen-xl mx-auto xl:mt-24" + logo}
          style={`background-image: url(${asset("/cg-logo-black.svg")})`}
        >
          <Component />
        </div>
      </div>
    </>
  );
}
