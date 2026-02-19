# Design System

Aligned to the current Figma frames (Home, Community, Post) and typography scale.

## Tokens
- Colors: dark theme default with light theme alternative, based on the Figma Colors boards and surface/elevation stacks.
- Color families: Red, Orange, Yellow, Lime, Green, Emerald, Cyan, Brand, Indigo, Purple, Pink, Rose, Gray (multi-step scales).
- Status colors: success, warning, danger, info (as shown in Status Colors).
- Text colors: primary, secondary, muted, inverse (as shown in Text Color).
- Icon colors: default, muted, inverse, active (as shown in Icon Colors).
- Outline colors: subtle, default, strong (as shown in Outline Colors).
- Surface and elevation: Surface plus 01-05 layers for depth in dark and light themes.
- Typography families: Satoshi (primary), Cal Sans (secondary).
- Typography sizes (token -> px): 3XS 12, 2XS 14, XS 16, SM 20, MD 24, LG 32, XL 40, 2XL 44, 3XL 48, 4XL 64, Display 80, Giant 96.
- Typography weights: Regular 400, Medium 500, Bold 700.
- Typography line heights: Default 100%, XS 150%.
- Spacing: 4px grid.
- Radius: 6/10/16 (cards and inputs).
- Shadow: soft elevation for cards and menus.
- Icon size: 20-24px for action icons.

## Layout
- Left sidebar with brand, primary nav, and community list.
- Top bar with theme toggle and user avatar.
- Main content column with cards and feed stack.

## Components
- SidebarNav (brand, nav item, community list item).
- TopBar (IconButton, Avatar).
- StatCard (Home summary cards).
- PostCard (community badge, title, excerpt, actions).
- CommunityHeader (banner, avatar, title, description, metadata, join/create actions).
- PostHeader (author avatar, handle, timestamp).
- CoverImage (post hero media).
- CommentComposer (simple input + rich editor variant).
- CommentThread (nested indentation with connector line).
- CommentCard (avatar, author, badges, text, actions, menu).
- Badge (community, Autor, Resposta).
- Button (primary: Criar post/Responder, secondary: Entrou, ghost).
- IconButton (up/downvote, reply, menu, theme).
- Menu (Salvar post, Compartilhar, Reportar, Excluir).
- EmptyState, Loading.

## States
- Default, hover, focus, disabled.
- Selected/active nav item.
- Joined state for community.
- Menu open/active item.
- Threaded reply indentation and connectors.
- Loading, empty, error.

## Accessibility
- Focus visible, adequate contrast in dark/light themes.
- Keyboard access for menus and reply actions.
