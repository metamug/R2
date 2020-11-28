import AboutIcon from 'assets/images/menu/about-icon.svg'
import APIDocsIcon from 'assets/images/menu/api-docs-icon.svg'
import ErrorIcon from 'assets/images/menu/errors-icon.svg'
import ResourcesIcon from 'assets/images/menu/resources-icon.svg'

export const navigationConfig = {
  menu: {
    resources: {
      path: '/resources',
      src: ResourcesIcon,
      alt: 'resources icon',
      title: 'Resources',
    },
    docs: {
      path: `/docs/${localStorage.defaultItem}`,
      src: APIDocsIcon,
      alt: 'api docs icon',
      title: 'API Docs',
    },
    help: {
      path: '/help',
      src: AboutIcon,
      alt: 'bout icon',
      title: 'About',
    },
  },
}
