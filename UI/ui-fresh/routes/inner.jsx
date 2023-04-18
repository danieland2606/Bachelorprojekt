export const handler = {
  async POST(req, ctx) {
    const data = await req.formData();
    console.log(data);
    const myval = data.get("myval")?.toString();
    console.log(myval);
    return await ctx.render(myval);
  },
};

export default function IframeInner({ data }) {
  return (
    <>
      <h1>{data}</h1>
    </>
  );
}
