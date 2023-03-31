export async function documentReady() {
  return new Promise((resolve, reject) => {
    if (document.readyState !== 'loading') {
      resolve();
    }
    document.addEventListener('DOMContentLoaded', () => {resolve();});
  });
}
