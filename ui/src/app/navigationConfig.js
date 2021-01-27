import AboutIcon from 'assets/images/menu/about-icon.svg'
import APIDocsIcon from 'assets/images/menu/api-docs-icon.svg'
import LogoutIcon from 'assets/images/menu/logout-icon.svg'
import ResourcesIcon from 'assets/images/menu/resources-icon.svg'
import ResourceManagerIcon from 'assets/images/menu/resourceManager-icon.svg'

export const navigationConfig = {
  menu: {
    resources: {
      path: '/resources',
      src: ResourcesIcon,
      alt: 'resources icon',
      title: 'Resources',
    },
    resourceManager: {
      path: '/resource-manager',
      src: ResourceManagerIcon,
      alt: 'resource-manager icon',
      title: 'Resource Manager',
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
      alt: 'about icon',
      title: 'About',
    },
    logout: {
      path: '/logout',
      src: LogoutIcon,
      alt: 'logout icon',
      title: 'Log Out',
    },
  },
}
