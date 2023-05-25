import { asset, Head } from "$fresh/runtime.ts";
import { AppProps } from "$fresh/src/server/types.ts";

export default function App({ Component }: AppProps) {
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
        class="absolute w-full min-w-full min-h-full overflow-x-hidden"
      >
        <div class="relative p-5 pt-6 max-w-screen-xl mx-auto xl:mt-24">
          <img
            src={asset("/cg-logo-black.svg")}
            class="absolute top-0 right-0 w-[240px] md:w-[320px] xl:w-[350px]"
          />
          <Component />
        </div>
      </div>
    </>
  );
}
